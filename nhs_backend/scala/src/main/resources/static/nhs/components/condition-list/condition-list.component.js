// Register `conditionList` component, along with its associated controller and template
angular
    .module('nhsConditionsApp')
    .component(
        'conditionList',
        {
            templateUrl : "nhs/components/condition-list/condition-list.template.html",
            controller : [
                'ConditionsSearch',
                function ConditionListController(ConditionsSearch) {
                    this.searchQuery = undefined;
                    this.conditions = [];

                    this.submit = function() {
                        if (this.searchQuery) {
                            console.log('Submitting a search query:',
                                this.searchQuery);
                            this.conditions = ConditionsSearch
                                .query({ query : this.searchQuery
                                });
                        } else {
                            this.conditions = [];
                        }
                    };
                } ]
        });