function OperadorController($state, $scope, OperadoresService) {
	var vm = this;
	vm.titulo = 'Operador'

	vm.salvar = function () {		
		OperadoresService.salvar(vm.operador).then(resp => {
			$scope.$emit('atualizarListaOperadores');
			$state.go('operadores');
		});			
	}
}
angular.module('app').component('operador', {
	templateUrl: 'operadores/operador.html',
	bindings: { operador: '<' },
	controller: OperadorController,
	controllerAs: 'ctrl'
})