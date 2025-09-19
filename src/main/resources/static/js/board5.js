 function delete5BoardFromData(element){

    const id = element.getAttribute('data-board-id');
    const loginId = element.getAttribute('data-login-id');
    const boardAuthor = element.getAttribute('data-board-author');


    if(loginId!==boardAuthor){
        alert('본인 게시물만 삭제 가능합니다.');
        return false;
    }
    if(confirm(id + "번 게시물을 정말 삭제하시겠습니까?")){
        $.ajax({
            url : '/delete5Board/' +id,
            type : 'DELETE',
            success : function(result){
                alert('성공적으로 삭제되었습니다.');
                window.location.reload();
            },
            error : function(xhr,status,error){
                alert('삭제에 실패했습니다. 오류 : '+xhr.responseText);
                console.error("error",error);
            }
        });
    }

 }