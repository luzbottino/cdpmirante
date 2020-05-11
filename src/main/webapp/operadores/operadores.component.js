function OperadoresController($state, $scope, OperadoresService) {
	var vm = this;

	vm.titulo = 'Lista de Operadores'

	vm.excluir = function (operador) {
		OperadoresService.excluir(operador.id).then(resp => {
			OperadoresService.buscarTodos().then(resp => {
				this.operadores = resp;
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