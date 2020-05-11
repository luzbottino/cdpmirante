angular.module('app').service('OperadoresService', function($http) {
	var service = {
		buscarTodos: function() {
			return $http.get('rest/operadores', {cache: true})
				.then(function(resp) {
					return resp.data;
				})
		},
		salvar: function(operador) {
			return $http.post('rest/operadores', operador).then(function(resp) {
				return resp.data;
			})
		},
		excluir: function(id) {
			return $http.delete('rest/operadores/' + id).then(function(resp) {
				return resp.data;
			})
		}
	}

	return service;
});