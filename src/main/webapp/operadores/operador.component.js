function OperadorController($state, $scope, OperadoresService) {
	var ctrl = this;
	
	ctrl.titulo = 'Operador'

	ctrl.salvar = function () {		
		OperadoresService.salvar(ctrl.operador).then(resp => {
			$scope.$emit('atualizarListaOperadores');
			$state.go('home.operadores');
		});			
	}
}
angular.module('app').component('operador', {
	templateUrl: 'operadores/operador.html',
	bindings: { operador: '<' },
	controller: OperadorController,
	controllerAs: 'ctrl'
})