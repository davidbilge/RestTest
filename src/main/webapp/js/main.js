function calculatorController($scope, $window, Products) {
    $scope.openPopoutMenu = '';

    $scope.setOpenPopoutMenu = function (id) {
        if ($scope.openPopoutMenu == id) {
            $scope.openPopoutMenu = '';
        } else {
            $scope.openPopoutMenu = id;
        }
    }

    $scope.products = Products.query();

    $scope.setProductName = function (name) {
        $scope.selectedProductName = name;
        $scope.selectedProductBgImage = 'url(img/product-bgs/' + name + '.jpg)';
        $scope.product = Products.get({productName: name});
    }

    $scope.setProductName('booklet');
}

angular.module('calculator.directive', []);

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
