homeController = ($scope, $http, $location, $timeout) ->
  timer = false

  $scope.doSearch = ->
    $timeout.cancel(timer) if timer

    timer = $timeout( ->
      $scope.refresh()
      timer = null
    , 250)


  $scope.refresh = ->
    $location.url "?q=#{$scope.search}";
    $http.get("/geeks?q=#{$scope.search}").success (json) ->
      $scope.geeks = json

  $scope.search = $location.search().q || ''

  $scope.clearGeek = () ->
    $scope.geek =
      prenom: ''
      nom: ''
      like1: ''
      like2: ''
      like3: ''

  $scope.clearGeek()

  $scope.mode = 'default'

  $scope.setMode = (mode) ->
    $scope.mode = mode

  $scope.inscription = (geek) ->
    g = { prenom: geek.prenom, nom: geek.nom, email: geek.email, likes: [] }
    for num in [1..3]
      do (num) ->
        if geek['like' + num] != ''
          g.likes.push(geek['like' + num])

    console.log 'inscription', g
    $http.post("/geeks", g).success (geek) ->
      $scope.geek = geek
      $scope.setMode('authenticated')

  $scope.logout = () ->
    $scope.clearGeek()
    $scope.setMode('default')

  $scope.refresh()
