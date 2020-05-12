var app = angular.module('app', ['ngResource', 'ngGrid', 'ui.bootstrap', 'ui.router', 'ui.router.state.events', 'ngStorage']);

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
		setPerfil: function (perfil) {
			$localStorage.perfil = perfil;
		},
		getPerfil: function () {
			return $localStorage.perfil
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

app.factory('interceptorAutenticacao', ['$q', '$location', 'autenticacao', function ($q, $location, autenticacao) {
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
				$location.path('/login');
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
			resolve: {
				operador: function (operadores, $stateParams) {
					return operadores.find(function (operador) {
						return operador.id == $stateParams.id;
					});
				}
			}
		},
		{
			name: 'home.operadores.editar',
			url: '/{id}',
			component: 'operador',
			resolve: {
				// TODO: buscar no servidor
				operador: function (operadores, $stateParams) {
					return angular.copy(operadores.find(function (operador) {
						return operador.id == $stateParams.id;
					}));
				}
			}
		},
		{
			name: 'home.pessoas',
			url: 'pessoas',
			component: 'pessoas',
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

app.run(['$state', '$rootScope', 'autenticacao', '$q' ,'$timeout', function ($state, $rootScope, autenticacao, $q, $timeout) {
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