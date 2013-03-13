function calculatorController($scope) {

}

angular.module('calculator.directive', [])
    .directive('popoutTrigger', function() {
        return {
                link: function(scope, element, attrs) {
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

angular.module('calculator', ['calculator.directive']);
