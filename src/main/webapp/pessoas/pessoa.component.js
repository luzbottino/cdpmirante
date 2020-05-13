function PessoaController($state, $scope, PessoasService, autenticacao, TelefonesService) {
	var ctrl = this;
	
	ctrl.titulo = 'Pessoa'
	ctrl.perfil = autenticacao.getPerfil();
	
	if(!ctrl.pessoa.id) {
		ctrl.pessoa.login = autenticacao.getLogin();
		ctrl.pessoa.telefones = [];
	}

	ctrl.salvar = function () {		
		PessoasService.salvar(ctrl.pessoa).then(resp => {
			$scope.$emit('atualizarListaPessoas');
			$state.go('home.pessoas');
		});			
	}

	ctrl.adicionarTelefone = function() {
		ctrl.pessoa.telefones.push({
			ddd: '',
			numero: '',
			dataCadastro: '',
			login: autenticacao.getLogin(),
			tipo: ''
		});
	}

	ctrl.excluirTelefone = function(telefone) {
		if(telefone && telefone.id) {
			TelefonesService.excluir(ctrl.pessoa.id, telefone.id)
		}
		var remover = ctrl.pessoa.telefones.indexOf(telefone);
		ctrl.pessoa.telefones.splice(remover, 1);
	}
}
angular.module('app').component('pessoa', {
	templateUrl: 'pessoas/pessoa.html',
	bindings: { pessoa: '<' },
	controller: PessoaController,
	controllerAs: 'ctrl'
})