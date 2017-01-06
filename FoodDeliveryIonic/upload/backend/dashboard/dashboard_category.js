angular.module('myApp.contacts', ['ngRoute'])


.controller('DashCtrl', ['$scope','$firebaseArray','loginService', function($scope,$firebaseArray,loginService) {
   
   var ref = new Firebase('https://<your-firebase-app>.firebaseio.com/orders')
    $scope.orders = $firebaseArray(ref);
    var uref = new Firebase('https://<your-firebase-app>.firebaseio.com/users')
     
    $scope.users = $firebaseArray(uref);
}])

.controller('DashCatCtrl', ['$scope','$firebaseArray','loginService','toastr', function($scope,$firebaseArray,loginService,toastr) {
     var ref = new Firebase('https://<your-firebase-app>.firebaseio.com/category')
    $scope.cats = $firebaseArray(ref);
    
     $scope.addShowForm = true;
    $scope.editShowForm = false;
    $scope.ShowEditForm = function(cat){
        $scope.addShowForm = false;
        $scope.editShowForm = true;
        $scope.id = cat.id;
        $scope.catid = cat.$id;
        $scope.name = cat.name;
        $scope.image = cat.image;
        
        $scope.editCat=function(){
                var id = $scope.catid;
                var record = $scope.cats.$getRecord(id);
                record.id = $scope.id;
                record.name = $scope.name;
                record.image = $scope.image;
                   $scope.cats.$save(record).then(function(ref){
                       toastr.success('Done');
                                           console.log(ref.key);
                                           });
                
               }
    }
    
    $scope.cancelCat = function(){
        $scope.addShowForm = true;
    $scope.editShowForm = false;
        
    }
    ///dashboard_add_category
    $scope.add=function(){
		$location.path('/dashboard_add_category');
	}
     $scope.addCat = function(){
        
        
        $scope.cats.$add({
            id: $scope.id,
            name: $scope.name,
            image:$scope.image
            
            
        }).then(function(ref){
            var id= ref.key();
            
            
            console.log('adding cat ' +id);
            $scope.id ='';
            $scope.name ='';
            $scope.image ='';
        });
        
        
    }
    
    
    
   $scope.removeCat = function(cat){
       $scope.cats.$remove(cat);
       
   } 
   $scope.logout=function(){
		loginService.logout();
	}
    
    }])
.controller('OrderCtrl', ['$scope','$firebaseArray','loginService','$firebaseObject','toastr', function($scope,$firebaseArray,loginService,$firebaseObject,toastr) {
     var ref = new Firebase('https://<your-firebase-app>.firebaseio.com/orders')
    $scope.orders = $firebaseArray(ref);
       $scope.addShowForm = true;
       $scope.editShowForm = false;
 
            $scope.ShowEditForm = function(cat){
                 var uref = new Firebase('https://<your-firebase-app>.firebaseio.com/users')
$scope.addresses=$firebaseArray(uref.child(cat.user_id).child("address"));
                
                $scope.addShowForm = false;
                $scope.editShowForm = true;
                $scope.id = cat.$id;
                $scope.userid=cat.user_id;
                $scope.name = cat.user_name;
                $scope.product_name = cat.product_name;
                $scope.item_qty = cat.item_qty;
                $scope.payment= cat.payment_id;
                $scope.adress= cat.address_id;
                $scope.status= cat.status;
                $scope.time=cat.time;
                
                //Confirmed Status
               $scope.ConfirmedStatus=function(stat){
                var id = $scope.id;
                var record = $scope.orders.$getRecord(id);
                record.status=stat;
                   $scope.orders.$save(record).then(function(ref){
                       toastr.warning('Status', 'Confirmed');
                       $scope.addShowForm = true;
    $scope.editShowForm = false;
                                           console.log(ref.key);
                                           });
                console.log(record.status);
               }
               //Delivered Status
               $scope.DeliveredStatus=function(stat){
        var id = $scope.id;
                var record = $scope.orders.$getRecord(id);
                record.status=stat;
                   $scope.orders.$save(record).then(function(ref){
                       
                      toastr.success('Status', 'Delivered');
                                           console.log(ref.key);
                                           });
                console.log(record.status);
            
    }
               
               //Refused Status
   $scope.RefusedStatus=function(stat){
        var id = $scope.id;
                var record = $scope.orders.$getRecord(id);
                record.status=stat;
                   $scope.orders.$save(record).then(function(ref){
                        toastr.error('Status', 'Refused');
                                           console.log(ref.key);
                                           });
                console.log(record.status);
        
    }
            }
           
   $scope.logout=function(){
		loginService.logout();
	}
    
    }])


.controller('SlidCtrl', ['$scope','$firebaseArray','loginService','toastr', function($scope,$firebaseArray,loginService,toastr) {
     var ref = new Firebase('https://<your-firebase-app>.firebaseio.com/slider')
    $scope.sls = $firebaseArray(ref);

    $scope.addShowForm = true;
    $scope.editShowForm = false;
   
        $scope.ShowEditForm = function(sl){
            
             $scope.addShowForm = false;
            $scope.editShowForm = true;
            
            $scope.id = sl.$id;
            console.log($scope.id);
            $scope.title = sl.title;
            $scope.description = sl.description;
            $scope.image = sl.image;
            
            $scope.editSl=function(){
                var id = $scope.id;
                var record = $scope.sls.$getRecord(id);
                
                record.title = $scope.title;
                record.description = $scope.description;    
                record.image = $scope.image;
                
                   $scope.sls.$save(record).then(function(ref){
                       toastr.success('Done');
                       $scope.addShowForm = true;
                        $scope.editShowForm = false;
                                           console.log(ref.key);
                                           });
                
               }
            
        }
        $scope.cancelSl = function(){
            $scope.addShowForm = true;
    $scope.editShowForm = false;
            
        }
    $scope.removeSL = function(sl){
       $scope.sls.$remove(sl);
       
   } 
    $scope.logout=function(){
		loginService.logout();
	}
 }])
.controller('DashSlCtrl', ['$scope','$firebaseArray','loginService','toastr', function($scope,$firebaseArray,loginService,toastr) {
     var ref = new Firebase('https://<your-firebase-app>.firebaseio.com/slider')
    $scope.sls = $firebaseArray(ref);
      
        
        
         $scope.addSl = function(){
        
        
        $scope.sls.$add({
            
                title : $scope.title,
                description : $scope.description,   
                image : $scope.image,
                
            
            
        }).then(function(ref){
            var id= ref.key();
            toastr.success('Done');
            
         
        });
        
        
    }
      
    
    $scope.logout=function(){
		loginService.logout();
	}
    }])

.controller('DashRecCtrl', ['$scope','$firebaseArray','loginService','toastr', function($scope,$firebaseArray,loginService,toastr) {
     var ref = new Firebase('https://<your-firebase-app>.firebaseio.com/menu')
    $scope.recs = $firebaseArray(ref);
    var cref = new Firebase('https://<your-firebase-app>.firebaseio.com/category')
    $scope.catids = $firebaseArray(cref);
      $scope.addRec = function(){
        
    
        
        $scope.recs.$add({
            
                name : $scope.name,
                description : $scope.description,
                shippingtime: $scope.shtime,
                image : $scope.image,
                category : $scope.category, 
                price : $scope.price  
            
            
        }).then(function(ref){
            var id= ref.key();
            toastr.success('Done');
            
            console.log('adding rec ' +id);
         
        });
        
        
    }
     
    $scope.logout=function(){
		loginService.logout();
	}
    }])
.controller('UsersCtrl', ['$scope','$firebaseArray','loginService', function($scope,$firebaseArray,loginService) {
     var ref = new Firebase('https://<your-firebase-app>.firebaseio.com/users')
     
    $scope.users = $firebaseArray(ref);
    
    $scope.addShowForm = true;
    $scope.editShowForm = false;
   
        $scope.ShowEditForm = function(user){
            var uref = new Firebase('https://<your-firebase-app>.firebaseio.com/users')
            
            $scope.addresses=$firebaseArray(uref.child(user.$id).child("address"));
            console.log($scope.addresses);
            $scope.addShowForm = false;
            $scope.editShowForm = true;
            $scope.id = user.$id;
            $scope.name = user.name;
            $scope.email = user.email;
            $scope.telephone = user.telephone;
            $scope.address = user.address;
        }
    
    
    $scope.cancelRec = function(){
        
    $scope.addShowForm = true;
    $scope.editShowForm = false;
        
    } 
   
   $scope.logout=function(){
		loginService.logout();
	}
    
    }])

.controller('RecCtrl', ['$scope','$firebaseArray','loginService','toastr', function($scope,$firebaseArray,loginService,toastr) {
     var ref = new Firebase('https://<your-firebase-app>.firebaseio.com/menu')
    $scope.recs = $firebaseArray(ref);
     var cref = new Firebase('https://<your-firebase-app>.firebaseio.com/category')
    $scope.catids = $firebaseArray(cref);
     $scope.addShowForm = true;
    $scope.editShowForm = false;
   
        $scope.ShowEditForm = function(rec){
            $scope.idrec=rec.$id;
            $scope.addShowForm = false;
            $scope.editShowForm = true;
            $scope.name = rec.name;
            $scope.description = rec.description;
            $scope.category = rec.category;
            $scope.image = rec.image;
            $scope.price = rec.price;
            
                $scope.editRec=function(){
                var id = $scope.idrec;
                var record = $scope.recs.$getRecord(id);
                
                record.name = $scope.name;
                record.description = $scope.description;    
                record.image = $scope.image;
                record.category = $scope.category; 
                record.price = $scope.price;     
                   $scope.recs.$save(record).then(function(ref){
                       toastr.success('Done');
                       $scope.addShowForm = true;
                        $scope.editShowForm = false;
                                           console.log(ref.key);
                                           });
                
               }

        }
        
   
    
    
   $scope.removeRec = function(rec){
       $scope.recs.$remove(rec);
       
   } 
   $scope.logout=function(){
		loginService.logout();
	}
    
    }]);