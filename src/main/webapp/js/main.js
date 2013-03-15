function calculatorController($scope, $window, Products) {
    $scope.products = Products.query();

    $scope.selectedProductName = 'booklet';

    $scope.setProductName = function(name) {
        $scope.selectedProductName = name;
    }
}

angular.module('calculator.directive', [])
    .directive('popoutTrigger', function () {
        return {
            link: function (scope, element, attrs) {
                element.bind('click', toggle);

                scope.visible = null;

                function toggle() {
                    if (scope.visible != null) {
                        scope.visible.removeClass('popout-open');
                        scope.visible.addClass('popout-closed');
                    }

                    var thisPopout = element.siblings('.popout');

                    if (scope.visible != null && scope.visible[0] == thisPopout[0]) {
                        scope.visible = null;
                    } else {
                        scope.visible = thisPopout;
                        scope.visible.removeClass('popout-closed');
                        scope.visible.addClass('popout-open');
                    }

                }
            }
        };

    });

angular.module('calculator.services', ['ngResource']).
    factory('Products', function ($resource) {
        return $resource('http://localhost\\:8080/resttest/api/products/:productName/', {}, {
            query: {method: 'GET', params: {productName: '@name'}, isArray: true}
        });
    });

angular.module('calculator.routers', [])
    .config(function ($routeProvider) {
        $routeProvider
            .when('/products/:selectedProduct', {controller: calculatorController, reloadOnSearch: false})
            .when('/products', {redirectTo: '/products/booklet'})
            .otherwise({redirectTo: '/products/booklet'});
    });

angular.module('calculator', ['calculator.directive', 'calculator.services']);
