function OperadorController($state, OperadoresService) {
	this.titulo = 'Operador'

	this.salvar = function () {		
		OperadoresService.salvar(this.operador);
		$state.reload();			
		$state.go('operadores', {}, {reload: 'operadores'});
	}
}
angular.module('app').component('operador', {
	templateUrl: 'operadores/operador.html',
	bindings: { operador: '<' },
	controller: OperadorController
})