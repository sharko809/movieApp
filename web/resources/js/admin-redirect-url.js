/**
 * Created by dsharko on 8/9/2016.
 */

(function () {
    $(document).ready(function () {
        var items = document.getElementsByName('redirectFrom');
        if (items != null) {
            for (var i = 0; i < items.length; i++) {
                items[i].value = document.location.href;
            }
        }
    });
})();
