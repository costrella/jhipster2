'use strict';

describe('Controller Tests', function() {

    describe('Person Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPerson, MockStore, MockRaport, MockWeek;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPerson = jasmine.createSpy('MockPerson');
            MockStore = jasmine.createSpy('MockStore');
            MockRaport = jasmine.createSpy('MockRaport');
            MockWeek = jasmine.createSpy('MockWeek');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Person': MockPerson,
                'Store': MockStore,
                'Raport': MockRaport,
                'Week': MockWeek
            };
            createController = function() {
                $injector.get('$controller')("PersonDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cechiniApp:personUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
