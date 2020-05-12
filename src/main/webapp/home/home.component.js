function HomeController($state, autenticacao) {
	var ctrl = this;
	
	ctrl.titulo = 'Início';
	ctrl.usuario = autenticacao.getNome();
	
	ctrl.sair = function() {
		autenticacao.sair();	
		$state.go('login');	
	};
}
angular.module('app').component('home', {
	templateUrl: 'home/home.html',	
	controller: HomeController,
	controllerAs: 'ctrl'
})