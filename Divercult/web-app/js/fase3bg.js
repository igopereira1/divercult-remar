$('#fase3form').submit(function (e) {
    e.preventDefault();    
    var formData = new FormData(this);

    $.ajax({
        url: '/divercult/fase3/saveImage',
        type: 'POST',
        data: formData,
        success: function (data) {
        window.top.location.href = data
        },
        cache: false,
        contentType: false,
        processData: false
    });
});
