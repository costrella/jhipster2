'use strict';

describe('Controller Tests', function() {

    describe('Raport Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockRaport, MockPerson, MockStore;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockRaport = jasmine.createSpy('MockRaport');
            MockPerson = jasmine.createSpy('MockPerson');
            MockStore = jasmine.createSpy('MockStore');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Raport': MockRaport,
                'Person': MockPerson,
                'Store': MockStore
            };
            createController = function() {
                $injector.get('$controller')("RaportDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipsterApp:raportUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
