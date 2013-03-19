function calculatorController($scope, $http, Products) {
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

angular.module('calculator.filter', ['calculator.services']).filter('i18n', function(I18n) {
    return function(input) {
        return I18n.translate(input);
    };
});

angular.module('calculator.services', ['ngResource']).
    factory('Products', function ($resource) {
        return $resource('http://localhost\\:8080/resttest/api/products/:productName/', {}, {
            query: {method: 'GET', params: {productName: '@name'}, isArray: true}
        });
    }).
    factory('I18n', function ($http) {
        var internationalize = {
            dictionary: undefined,
            loadDictionary: function loadDictionary(iso_lang) {
                $http.get('i18n/' + iso_lang + ".json").success(function(data) {
                    internationalize.dictionary = data;
                });
            },
            translate: function translate(key) {
                if (typeof internationalize.dictionary === "object" && key in internationalize.dictionary) {
                    return internationalize.dictionary[key];
                } else {
                    // TODO: Push error to server

                    return 'i18nError: ' + key;
                }
            }
        };

        internationalize.loadDictionary('de_de');

        return internationalize;
    });

angular.module('calculator.routers', [])
    .config(function ($routeProvider) {
        $routeProvider
            .when('/products/:selectedProduct', {controller: calculatorController, reloadOnSearch: false})
            .when('/products', {redirectTo: '/products/booklet'})
            .otherwise({redirectTo: '/products/booklet'});
    });

angular.module('calculator', ['calculator.directive', 'calculator.services', 'calculator.filter']);
