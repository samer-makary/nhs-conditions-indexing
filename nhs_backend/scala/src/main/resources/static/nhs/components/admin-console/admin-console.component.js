// Register `adminConsole` component, along with its associated controller and template
angular
    .module('nhsConditionsApp')
    .component(
        'adminConsole',
        {
            templateUrl : "nhs/components/admin-console/admin-console.template.html",
            controller : [
                '$http',
                function AdminConsoleController($http) {
                    var that = this;
                    this.reindexBtnLbl = "Rebuild Index";
                    this.setIndexSize = function() {
                        that.indexSize = "N/A!";
                        $http({
                            method: 'GET',
                            url: 'conditions/count',
                            transformResponse: []
                        }).then(
                            function successCallback(response) {
                                console.log("setIndexSize received:", response.data);
                                that.indexSize = response.data;
                            },
                            function errorCallback(response) {
                                console.error(response);
                            }
                        );
                    };

                    this.rebuildIndex = function() {
                        that.reindexBtnLbl = "Building index..."
                        $http({
                            method: 'POST',
                            url: 'conditions/loadIndex',
                            transformResponse: [],
                        }).then(
                            function successCallback(response) {
                                console.log("rebuildIndex received:", response.data);
                                that.setIndexSize()
                                alert("Rebuilding Index: " + response.data);
                                that.reindexBtnLbl = "Rebuild Index";
                            },
                            function errorCallback(response) {
                                console.error(response);
                                alert("Rebuilding Index: Failed!");
                                that.reindexBtnLbl = "Rebuild Index";
                            }
                        );
                    };

                    this.setIndexSize();
                } ]
        });