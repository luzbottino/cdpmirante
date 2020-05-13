angular.module('app').service('TelefonesService', function ($http) {
	var service = {		
		excluir: function (idPessoa, idTelefone) {
			return $http.delete('rest/pessoas/' + idPessoa +'/telefones/' + idTelefone).then(function (resp) {
				return resp.data;
			})
		}
	}

	return service;
});