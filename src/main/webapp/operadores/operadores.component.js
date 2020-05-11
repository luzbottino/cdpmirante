function OperadoresController($state, $scope, OperadoresService) {
	var ctrl = this;

	ctrl.titulo = 'Lista de Operadores'

	ctrl.excluir = function (operador) {
		OperadoresService.excluir(operador.id).then(resp => {
			OperadoresService.buscarTodos().then(resp => {
				ctrl.operadores = resp;
			})
		});
	}

	$scope.$on('atualizarListaOperadores', function (event, data) {
		OperadoresService.buscarTodos().then(resp => {
			$scope.ctrl.operadores = resp;
		});
	});
}

angular.module('app').component('operadores', {
	templateUrl: 'operadores/operadores.html',
	bindings: { operadores: '<' },
	controller: OperadoresController,
	controllerAs: 'ctrl'	

})