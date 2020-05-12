angular.module('app').service('AutenticacaoService', function ($http, $httpParamSerializerJQLike) {
	var service = {
		autenticar: function (operador) {
			return $http(
				{
					url: 'rest/login',
					method: 'POST',
					data: $httpParamSerializerJQLike({ login: operador.login, senha: operador.senha }), // Make sure to inject the service you choose to the controller
					headers: {
						'Content-Type': 'application/x-www-form-urlencoded' // Note the appropriate header
					}
				});
		}
	}

	return service;
});