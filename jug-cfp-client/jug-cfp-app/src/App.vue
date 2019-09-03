<template>
  <div id="app">
    <b-container>
      <div>
        <b-jumbotron v-if="eventoSelecionado" header="JUG Vale" :lead="eventoSelecionado.nome">
          <p class="justified">{{ eventoSelecionado.descricao }}</p>
          <p>
            <b>{{ eventoSelecionado.local }}</b>
          </p>
          <div class="float-right">
            <b-form-select class="select-variant" v-model="eventoSelecionado" :options="options" size="sm"></b-form-select>
            <b-button variant="primary" href="http://jugvale.com">Ir para o site</b-button>
          </div>
        </b-jumbotron>
        <b-jumbotron v-else header="JUG Vale" lead="Selecione um evento">
          <div class="float-right">
            <b-form-select class="select-variant" v-model="eventoSelecionado" :options="options" size="sm"></b-form-select>
            <b-button variant="primary" href="http://jugvale.com">Ir para o site</b-button>
          </div>
        </b-jumbotron>
      </div>
      <ListaParticipantes v-if="eventoSelecionado" :evento="eventoSelecionado" ref="ListaParticipantes" />
    </b-container>
  </div>
</template>

<script>
const axios = require('axios');

import ListaParticipantes from './components/ListaParticipantes.vue'

export default {
  name: 'app',
  data () {
    return {
      eventos: [],
      eventoSelecionado: null,
      isLoading: true
    }
  },
  watch: {
    eventoSelecionado () {
      this.$nextTick(() => {
        if (this.eventoSelecionado)
          this.$refs.ListaParticipantes.update()
      })
    }
  },
  components: {
    ListaParticipantes
  },
  computed: {
    options () {
      if (this.eventos)
        this.eventoSelecionado = this.eventos[0]

      return this._.map(this.eventos, (evnt) => {
        return { value: evnt, text: evnt.nome }
      })
    }
  },
  created () {
    axios.interceptors.request.use((config) => {
      this.isLoading = true;
      return config;
    }, (error) => {
      this.isLoading = false;
      return Promise.reject(error);
    })

    axios.interceptors.response.use((response) => {
      this.isLoading = false;
      return response;
    }, (error) => {
      this.isLoading = false;
      return Promise.reject(error);
    })
  },
  mounted () {
    axios
      .get('http://ec2-52-43-198-33.us-west-2.compute.amazonaws.com:8080/jug-cfp-server/rest/evento')
      .then(response => {
        response.data.sort(function(a, b) {
          let a_match = a.nome.trim().match(/\d+/)
          let b_match = b.nome.trim().match(/\d+/)
          let keyA, keyB = 0
          keyA = parseInt(a_match[0], 10)
          keyB = parseInt(b_match[0], 10)
          return keyB - keyA
        })

        this.eventos = response.data
      })
      .catch(error => console.log(error))
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
.justified {
  text-align: justify;
}
.select-variant {
  display: inline-block;
  width: fit-content;
}
</style>
