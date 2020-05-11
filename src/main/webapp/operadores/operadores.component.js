angular.module('app').component('operadores', {
		templateUrl: 'operadores/operadores.html',
		bindings: { operadores: '<' },
		controller: function() {
			this.titulo = 'Lista de Operadores'
		}
	})