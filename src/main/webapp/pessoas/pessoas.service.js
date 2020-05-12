angular.module('app').service('PessoasService', function ($http) {
	var service = {
		buscarTodos: function () {
			return $http.get('rest/pessoas')
				.then(function (resp) {
					return resp.data;
				});
		},
		buscarPorId: function (id) {
			return $http.get('rest/pessoas/' + id)
				.then(function (resp) {
					return resp.data;
				});
		},		
		salvar: function (operador) {
			return $http.post('rest/pessoas', operador).then(function (resp) {
				return resp.data;
			})
		},
		excluir: function (id) {
			return $http.delete('rest/pessoas/' + id).then(function (resp) {
				return resp.data;
			})
		}
	}

	return service;
});