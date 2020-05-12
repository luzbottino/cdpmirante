function PessoaController($state, $scope, PessoasService, autenticacao) {
	var ctrl = this;
	
	ctrl.titulo = 'Pessoa'
	ctrl.perfil = autenticacao.getPerfil();
	
	if(!ctrl.pessoa.id) {
		ctrl.pessoa.login = autenticacao.getLogin();
	}

	ctrl.salvar = function () {		
		PessoasService.salvar(ctrl.pessoa).then(resp => {
			$scope.$emit('atualizarListaPessoas');
			$state.go('home.pessoas');
		});			
	}
}
angular.module('app').component('pessoa', {
	templateUrl: 'pessoas/pessoa.html',
	bindings: { pessoa: '<' },
	controller: PessoaController,
	controllerAs: 'ctrl'
})