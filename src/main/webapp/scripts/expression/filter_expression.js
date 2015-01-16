vociboxApp.filter('gender', function(){
    return function (expression){
        if (expression.masculine && expression.feminine && expression.neuter){
            return "m/f/n";
        }
        else if (expression.masculine && expression.feminine && !expression.neuter){
            return "m/f";
        }
        else if (expression.masculine && !expression.feminine && expression.neuter){
            return "m/n";
        }
        else if (!expression.masculine && expression.feminine && expression.neuter){
            return "f/n";
        }
        else if (expression.masculine && !expression.feminine && !expression.neuter){
            return "m";
        }
        else if (!expression.masculine && expression.feminine && !expression.neuter){
            return "f";
        }
        else if (!expression.masculine && !expression.feminine && expression.neuter){
            return "n";
        }
    };
});
