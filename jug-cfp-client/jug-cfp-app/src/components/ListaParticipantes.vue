<template>
	<b-container>
    <div v-if="!isLoading">
      <b-row class="spaced-el">
        <b-col md="8" sm="12" class="spaced-el">
          <b-form-input v-model="search_q" placeholder="Procurar por participante" autofocus></b-form-input>
        </b-col>
        <b-col md="4" sm="12" class="spaced-el text-right">
          <b-form-checkbox
            id="isShowingOnlyShowedUpParticipantsStatus"
            v-model="isShowingOnlyShowedUpParticipantsStatus"
            name="isShowingOnlyShowedUpParticipantsStatus"
            value="1"
            unchecked-value="0"
          >
            {{ getCheckboxLabel }}
          </b-form-checkbox>
            <!-- <input type="checkbox" class="custom-control-input" id="defaultUnchecked">
            <label class="custom-control-label" for="defaultUnchecked">Default unchecked</label> -->
        </b-col>
      </b-row>
      <b-card
        :title="data.participante.nome"
        :sub-title="`Nível: ${data.participante.nivel || 'N/A'}`"
        class="spaced-el text-left"
        v-for="data in participantes" :key="`card-${data.id}`">
        <span>{{ formatDate(data.data) }}</span>
        <div class="float-right-on-top">

          <transition name="slide-fade">
            <div v-if="data.compareceu" >
              <lottie :options="defaultOptions" :height="80" :width="80" :prop-id="data.id" :ref="`lottie-${data.id}`"/>
            </div>
            <b-button variant="success" v-else @click="markAsShowedUp(data.id)">Marcar Compareceu</b-button>
          </transition>
        </div>
      </b-card>
    </div>
    <div v-else>
      <img id="LoadingDuke" src="../assets/duke-loading.gif"><br>
      Carregando...
    </div>
  </b-container>
</template>

<script>
import Lottie from './misc/lottie.vue'
import * as animationData from '../assets/check_lottie.json'

const axios = require('axios')
const moment = require('moment')

export default {
  name: 'lista-participantes',
  components: {
    'lottie': Lottie
  },
  watch: {
    participantes () {
    }
  },
  props: {
    evento: {
      type: Object,
      default: undefined
    }
  },
  data () {
    return {
      isShowingOnlyShowedUpParticipantsStatus: '0',
      listaParticipantes: [],
      animated: [],
      isLoading: true,
      search_q: '',
      defaultOptions: {
        animationData: animationData,
        autoplay: true,
        loop: false,
      },
      animationSpeed: 1
    }
  },
  computed: {
    participantes () {
      let participantes = this._.map(this.listaParticipantes, _.clone)

      if (this.search_q) {
        participantes = this._.filter(participantes, (item) => {
          return this.matchSearch(this.search_q, item)
        })
      }

      if (this.isShowingOnlyShowedUpParticipants) {
        participantes = this._.filter(participantes, ['compareceu', this.isShowingOnlyShowedUpParticipants])
      }

      return this._.orderBy(participantes, ['participante.nome'])
    },
    isShowingOnlyShowedUpParticipants () {
      return this.isShowingOnlyShowedUpParticipantsStatus === '1'
    },
    getCheckboxLabel () {
      return !this.isShowingOnlyShowedUpParticipants ?
        'Mostrar presentes' : 'Mostrar todos'
    }
  },
  created () {
    axios.interceptors.request.use((config) => {
      this.isLoading = true
      return config
    }, (error) => {
      this.isLoading = false
      return Promise.reject(error)
    })

    axios.interceptors.response.use((response) => {
      this.isLoading = false
      return response
    }, (error) => {
      this.isLoading = false
      return Promise.reject(error)
    })
  },
  mounted () {
    this.update()
  },
  methods: {
    finalState (anim) {
      anim.setDirection(-1)
      anim.goToAndStop(32 , true)
    },
    markAsShowedUp (index) {
      const participante = this._.find(this.listaParticipantes, ['id', index])

      if (!participante) {
        alert('erro ao marcar')
        return
      }

      participante.compareceu = true
      
      this.$nextTick(() => {
        const _this = this
        let keys = Object.keys(this.$refs)
        let key, participante, index, lottie = null

        for (var i = keys.length - 1; i >= 0; i--) {
          key = keys[i]
          lottie = this.$refs[key][0]
          index = lottie.$el.getAttribute("prop-id")
          participante = this.participantes.find((ptc) => {
            window.console.log(`participante: ${ptc.participante.nome} ${ptc.compareceu}`)
            return ptc.id === index
          })

          if (!participante)
            continue

          if (!participante.compareceu && _this.animated.indexOf(participante.id) < 0) {
            _this.finalState(lottie.anim)
            _this.animated.push(participante.id)
          }
        }
      })

      axios
        .post(`http://ec2-52-43-198-33.us-west-2.compute.amazonaws.com:8080/jug-cfp-server/rest/inscricao/${participante.id}/presenca`)
        .then(response => {
          participante.compareceu = true
        })
        .catch(error => console.log(error))
    },
    update () {
      axios
        .get(`http://ec2-52-43-198-33.us-west-2.compute.amazonaws.com:8080/jug-cfp-server/rest/evento/${this.evento.id}/inscritos`)
        .then(response => this.listaParticipantes = response.data)
        .catch(error => console.log(error))  

      // this.$forceUpdate()


      // this.listaParticipantes = 
    },
    formatDate (value, fmt) {
      if (value == null) return ''
      fmt = (typeof fmt == 'undefined') ? 'D\\/MM\\/YYYY \\à\\s HH:mm' : fmt
      return moment(value, 'YYYY-MM-DD HH:mm').format(fmt)
    },
    matchSearch(q, item) {
      let qs = []
      let nome = item.participante.nome.toLowerCase()

      q = q.toLowerCase()

      if (q.indexOf('%')) {
        qs = q.split('%').map((q_item) => q_item.trim())
      } else {
        qs.push(q.trim())
      }

      return this._.every(this._.map(qs, (search_q) => {
        return nome.indexOf(search_q) !== -1
      }))
    }
  }
}
</script>

<style lang="scss">
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

h1, h2 {
  font-weight: normal;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}
.card-title, .card-subtitle {
  text-align: left;
}
#LoadingDuke {
  opacity: 0.5;
}
.float-right-on-top {
  float: right!important;
  position: absolute;
  right: 10px;
  top: 10px;
}
.spaced-el {
  margin-bottom: 1rem;
}
/* Animações de entrada e saída podem utilizar diferentes  */
/* funções de duração e de tempo.                          */
.slide-fade-enter-active {
  transition: all .25s ease;
}
.slide-fade-leave-active {
  transition: all .25s cubic-bezier(.4, 0.15, 0.25, .4);
}
.slide-fade-enter, .slide-fade-leave-to
/* .slide-fade-leave-active em versões anteriores a 2.1.8 */ {
  transform: translateX(15px);
  opacity: 0.4;
}
</style>
