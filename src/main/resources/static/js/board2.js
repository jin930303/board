function delete2Board(id){
if(confirm(id + "번 게시물을 정말 삭제하시겠습니까?")){
        $.ajax({
            url : 'delete2Board/' + id,
            type : 'DELETE',
            success : function(result){
                alert('성공적으로 삭제되었습니다.');
                window.location.reload();
            },
            error : function(xhr, status, error){
                alert('삭제에 실패했습니다. 오류 : '+ error);
                console.error("error : ",error);
            }
        });
    }
}