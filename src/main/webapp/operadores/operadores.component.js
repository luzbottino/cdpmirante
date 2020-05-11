function OperadoresController($state, OperadoresService) {
	this.titulo = 'Lista de Operadores'
	
	this.excluir = function(operador) {
		OperadoresService.excluir(operador.id)				
		$state.go('operadores', {}, {reload: 'operadores'});	
	}
}

angular.module('app').component('operadores', {
		templateUrl: 'operadores/operadores.html',
		bindings: { operadores: '<' },
		controller: OperadoresController
	})