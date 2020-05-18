var app = angular.module('app', ['ngResource', 'ngGrid', 'ui.bootstrap', 'ui.router', 'ui.router.state.events', 'ngStorage', 'ngMask', 'Alertify']);

app.factory('autenticacao', ['$q', '$localStorage', function ($q, $localStorage) {

	return {
		getToken: function () {
			return $localStorage.token;
		},
		setToken: function (token) {
			$localStorage.token = token;
		},
		setNome: function (nome) {
			$localStorage.nome = nome;
		},
		getNome: function () {
			return $localStorage.nome;
		},
		setLogin: function (login) {
			$localStorage.login = login;
		},
		getLogin: function () {
			return $localStorage.login;
		},
		setPerfil: function (perfil) {
			$localStorage.perfil = perfil;
		},
		getPerfil: function () {
			return $localStorage.perfil;
		},
		setAdministrador: function (administrador) {
			$localStorage.administrador = administrador;
		},
		isAdministrador: function () {
			return $localStorage.administrador
		},
		sair: function () {
			delete $localStorage.token;
			delete $localStorage.nome;
			delete $localStorage.perfil;
			delete $localStorage.administrador;
			$q.when();
		}
	}
}]);

app.factory('interceptorAutenticacao', ['$q', '$state', 'autenticacao', 'Alertify', function ($q, $state, autenticacao, Alertify) {
	return {
		request: function (config) {
			config.headers = config.headers || {};

			if (autenticacao.getToken()) {
				config.headers['Authorization'] = autenticacao.getToken();
			}

			return config;
		},
		responseError: function (error) {
			if (error.status === 401 || error.status === 403) {
				$state.go('login');
			} else if(error.status === 400) {	
				for (erro in error.data) {					
					Alertify.error(`Erro: ${erro} ${error.data[erro]}`);
				 }				
			}
			return $q.reject(error);
		}
	};
}]);

app.config(function ($httpProvider) {
	$httpProvider.interceptors.push('interceptorAutenticacao');
});

app.config(function ($stateProvider, $urlRouterProvider) {

	$urlRouterProvider.otherwise('/');

	var states = [
		{
			name: 'home',
			url: '/',
			component: 'home',
			resolve: { autenticado: autenticado }
		},
		{
			name: 'home.operadores',
			url: 'operadores',
			component: 'operadores',
			administrador: true,
			resolve: {
				operadores: function (OperadoresService) {
					return OperadoresService.buscarTodos();
				}
			}
		},
		{
			name: 'home.operadores.salvar',
			url: '/',
			component: 'operador',
			administrador: true,
			resolve: {
				operador: function () {
					return {}
				}
			}
		},
		{
			name: 'home.operadores.editar',
			url: '/{id}',
			component: 'operador',
			administrador: true,
			resolve: {
				operador: function ($stateParams, OperadoresService) {
					return OperadoresService.buscarPorId($stateParams.id);
				}
			}
		},
		{
			name: 'home.pessoas',
			url: 'pessoas',
			component: 'pessoas',
			perfis: ['ANALISTA', 'GERENTE'],
			resolve: {
				pessoas: function (PessoasService) {
					return PessoasService.buscarTodos();
				}
			}
		},
		{
			name: 'home.pessoas.salvar',
			url: '/',
			component: 'pessoa',
			perfis: ['GERENTE'],
			resolve: {
				pessoa: function () {
					return {};
				}
			}
		},
		{
			name: 'home.pessoas.editar',
			url: '/{id}',
			component: 'pessoa',
			perfis: ['GERENTE'],
			resolve: {
				pessoa: function ($stateParams, PessoasService) {
					return PessoasService.buscarPorId($stateParams.id);
				}
			}
		},
		{
			name: 'home.acessonegado',
			url: 'restrito',
			component: 'acessonegado'
		},
		{
			name: 'login',
			url: '/login',
			component: 'login'
		}

	];

	states.forEach((state) => $stateProvider.state(state));

	function autenticado($q, autenticacao, $state, $timeout) {
		if (autenticacao.getToken()) {
			return $q.when()
		} else {

			$timeout(function () {
				$state.go('login')
			});

			return $q.reject()
		}
	}

});

app.run(['$state', '$rootScope', 'autenticacao', '$q', '$timeout', function ($state, $rootScope, autenticacao, $q, $timeout) {
	$rootScope.$on('$stateChangeStart', function (e, toState, toParams, fromState, fromParams) {

		if (toState.administrador) {

			if (!autenticacao.isAdministrador()) {
				$timeout(function () {
					$state.go('home.acessonegado')
				});

				return $q.reject()
			}
		}

	});
}]);