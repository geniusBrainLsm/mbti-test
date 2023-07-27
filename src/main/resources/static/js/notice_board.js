document.addEventListener("DOMContentLoaded", function() {
  var menuItems = document.querySelectorAll(".menu > li");
  menuItems.forEach(function(item) {
    var submenu = item.querySelector(".submenu");
    var liHeight = item.offsetHeight; // Get the height of each li element
    // Set the height of the submenu to match the calculated li height
    submenu.style.height = liHeight + "px";
  });
});
