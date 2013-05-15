homeController = ($scope, $http, $location) ->
  $scope.doSearch = ->
    $scope.refresh()

  $scope.refresh = ->
    $location.url "?q=#{$scope.search}";
    $http.get("/search?q=#{$scope.search}").success (json) ->
      $scope.geeks = json

  $scope.search = $location.search().q || ''

  $scope.geek =
    prenom: ''
    nom: ''
    like1: ''
    like2: ''
    like3: ''

  $scope.mode = 'default'

  $scope.setMode = (mode) ->
    $scope.mode = mode

  $scope.inscription = (geek) ->
    console.log 'inscription', geek

  $scope.refresh()
