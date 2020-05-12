function LoginController($state, AutenticacaoService, OperadoresService, autenticacao) {
	var ctrl = this;

	ctrl.titulo = 'Login';

	ctrl.operador = '';

	ctrl.logar = function () {
		AutenticacaoService.autenticar(ctrl.operador).success((data, status, headers, config) => {
			autenticacao.setToken(headers().authorization);

			OperadoresService.buscarTodos().then(resp => {
				var autenticado = resp.find(o => o.login == ctrl.operador.login);

				if (autenticado) {
					autenticacao.setNome(autenticado.nome);
					autenticacao.setPerfil(autenticado.perfil);
					autenticacao.setLogin((autenticado.login));
					autenticacao.setAdministrador(autenticado.administrador);
				}
			});

			$state.go('home');
		})

	}


}
angular.module('app').component('login', {
	templateUrl: 'autenticacao/login.html',
	controller: LoginController,
	controllerAs: 'ctrl'
})