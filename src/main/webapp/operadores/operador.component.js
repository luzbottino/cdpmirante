function OperadorController(OperadoresService) {
	this.titulo = 'Editar Operador'

	this.salvar = function () {		
		return OperadoresService.salvar(this.operador);
	}
}
angular.module('app').component('operador', {
	templateUrl: 'operadores/operador.html',
	bindings: { operador: '<' },
	controller: OperadorController
})