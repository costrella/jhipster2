'use strict';

describe('Controller Tests', function() {

    describe('Day Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockDay, MockWeek, MockStore, MockRaport;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockDay = jasmine.createSpy('MockDay');
            MockWeek = jasmine.createSpy('MockWeek');
            MockStore = jasmine.createSpy('MockStore');
            MockRaport = jasmine.createSpy('MockRaport');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Day': MockDay,
                'Week': MockWeek,
                'Store': MockStore,
                'Raport': MockRaport
            };
            createController = function() {
                $injector.get('$controller')("DayDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cechiniApp:dayUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
