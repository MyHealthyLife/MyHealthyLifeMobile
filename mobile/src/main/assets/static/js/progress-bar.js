 updateProgressBars = setInterval(function() {
    $('.progress .progress-bar').css("width",
              function() {
                  return $(this).attr("aria-valuenow") + "%";
              }
      )
  }, 5000);