// Register `adminConsole` component, along with its associated controller and template
angular
    .module('nhsConditionsApp')
    .component(
        'adminConsole',
        {
            templateUrl : "nhs/components/admin-console/admin-console.template.html",
            controller : [
                '$http', '$mdToast', '$mdDialog',
                function AdminConsoleController($http, $mdToast, $mdDialog) {
                    var that = this;
                    this.reindexBtnLbl = "Rebuild Index";
                    this.buildingIndex = false;
                    this.adminUsername = undefined;
                    this.adminPassword = undefined;

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

                    this.loginFailedToast = function() {
                        $mdToast.show(
                          $mdToast.simple()
                            .textContent('Administrator login, Failed!')
                            .position('top right')
                            .hideDelay(3000)
                        );
                    };

                    this.cancelLoginDialog = function() {
                        $mdDialog.cancel();
                        this.adminUsername = undefined;
                        this.adminPassword = undefined;
                        this.loginFailedToast();
                    };

                    this.confirmLoginDialog = function() {
                        $mdDialog.hide();
                        if (this.adminUsername && this.adminPassword) {
                            // creating base64 encoded String from username and password
                            var base64Creds = btoa(this.adminUsername + ':' + this.adminPassword);

                            // calling GET request for getting the user details
                            $http.get('/users/authorized', { headers : { 'Authorization' : 'Basic ' + base64Creds }})
                            .success(function(res) {
                                if (res == true) {
                                    // setting the same header value for all request calling from this app
                                    $http.defaults.headers.common['Authorization'] = 'Basic ' + base64Creds;
                                    $mdToast.show(
                                      $mdToast.simple()
                                        .textContent('Administrator credentials, Verified!')
                                        .position('top right')
                                        .hideDelay(3000)
                                    );
                                } else {
                                    console.error(res);
                                    that.loginFailedToast();
                                }
                            })
                            .error(function(error) {
                                that.loginFailedToast();
                            });
                        }
                        this.adminUsername = undefined;
                        this.adminPassword = undefined;
                    };

                    this.showLoginDialog = function() {
                        $mdDialog.show({
                          controller: function() { return that; },
                          controllerAs: 'ctrl',
                          templateUrl: 'nhs/components/admin-console/admin-login.dialog.html',
                          parent: angular.element(document.body),
                          clickOutsideToClose: true,
                        });
                    };

                    this.rebuildIndex = function() {
                        that.buildingIndex = true;
                        that.reindexBtnLbl = "Building index..."
                        $http({
                            method: 'POST',
                            url: 'conditions/loadIndex',
                            transformResponse: [],
                        })
                        .then(
                            function successCallback(response) {
                                console.log("rebuildIndex received:", response.data);
                                that.setIndexSize()
                                that.reindexBtnLbl = "Rebuild Index";
                                that.buildingIndex = false;
                                $mdToast.show(
                                  $mdToast.simple()
                                    .textContent('Rebuilding index, ' + response.data)
                                    .position('top right')
                                    .hideDelay(3000)
                                );
                            },
                            function errorCallback(response) {
                                console.error(response);
                                that.reindexBtnLbl = "Rebuild Index";
                                that.buildingIndex = false;
                                $mdToast.show(
                                  $mdToast.simple()
                                    .textContent('Rebuilding index, Failed!')
                                    .action('LOGIN')
                                    .highlightAction(true)
                                    .highlightClass('md-warn')
                                    .position('top right')
                                    .hideDelay(3000)
                                )
                                .then(function(response) {
                                    if (response == 'ok') {
                                        that.showLoginDialog();
                                    }
                                });
                            }
                        );
                    };

                    this.setIndexSize();
                } ]
        });