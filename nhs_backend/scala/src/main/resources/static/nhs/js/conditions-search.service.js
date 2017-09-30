// Register `search` service
angular.module('nhsConditionsApp').factory(
    'ConditionsSearch',
    [
        '$resource',
        function($resource) {
            return $resource('conditions/search', {}, { query : {
                method : 'GET', params : { query : '@query'
                }, isArray : true
            }
            });
        } ]);