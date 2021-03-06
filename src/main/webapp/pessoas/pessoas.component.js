function PessoasController($scope,autenticacao, PessoasService) {
	var ctrl = this;
	ctrl.titulo = 'Lista de Pessoas';
	ctrl.perfil = autenticacao.getPerfil();

	ctrl.pessoas.forEach(pessoa => {
		pessoa.dataNascimento = new Date(pessoa.dataNascimento).toLocaleDateString();
		pessoa.dataCadastro = new Date(pessoa.dataCadastro).toLocaleDateString();
	});

	ctrl.excluir = function (operador) {
		PessoasService.excluir(operador.id).then(resp => {
			PessoasService.buscarTodos().then(resp => {
				ctrl.pessoas = resp;
			})
		});
	}

	$scope.$on('atualizarListaPessoas', function (event, data) {
		PessoasService.buscarTodos().then(resp => {
			$scope.ctrl.pessoas = resp;
		});
	});
}

angular.module('app').component('pessoas', {
	templateUrl: 'pessoas/pessoas.html',
	bindings: { pessoas: '<' },
	controller: PessoasController,
	controllerAs: 'ctrl'
})