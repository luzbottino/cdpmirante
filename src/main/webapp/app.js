var app = angular.module('app', ['ngResource', 'ngGrid', 'ui.bootstrap', 'ui.router']);

app.config(function ($stateProvider, $urlRouterProvider) {
	var states = [
		{
			name: 'operadores',
			url: '/operadores',
			component: 'operadores',
			resolve: {
				operadores: function(OperadoresService) {
					return OperadoresService.buscarTodos();
				}
			}			
		},		
		{
			name: 'operadores.salvar',
			url: '/',
			component: 'operador',
			resolve: {
				operador: function(operadores, $stateParams) {
					return operadores.find(function(operador) {						
						return operador.id == $stateParams.id;
					});
				}
			}			
		},
		{
			name: 'operadores.editar',
			url: '/{id}',
			component: 'operador',
			resolve: {
				operador: function(operadores, $stateParams) {
					return operadores.find(function(operador) {						
						return operador.id == $stateParams.id;
					});
				}
			}			
		},
		{
			name: 'pessoas',
			url: '/pessoas',
			component: 'pessoas',						
		}		
	];
	states.forEach((state) => $stateProvider.state(state));
	$urlRouterProvider.otherwise('/');
});