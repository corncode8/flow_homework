function fetchCurrentIp() {
    fetch('/ip')
        .then(response => response.json())
        .then(data => {
            if (data.isSuccess) {
                document.getElementById('ipAddress').value = data.result.ip;
            } else {
                alert('현재 IP를 불러올 수 없습니다: ' + data.message);
            }
        })
        .catch(error => {
            console.error('Error fetching IP:', error);
            alert('현재 IP를 불러올 수 없습니다.');
        });
}

function saveIp(event) {
    event.preventDefault(); // 폼 제출 방지

    const ipAddress = document.getElementById('ipAddress').value;
    const description = document.getElementById('description').value;
    const startTime = new Date(document.getElementById('startTime').value).toISOString();
    const endTime = new Date(document.getElementById('endTime').value).toISOString();

    var requestData = {
        ip: ipAddress,
        description: description,
        startTime: startTime,
        endTime: endTime
    };

    fetch('/save', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestData)
    })
        .then(response => response.json())
        .then(data => {
            if (data.isSuccess) {
                alert('IP가 성공적으로 저장되었습니다.');
                toggleForm(); // 폼 숨기기
                location.reload(); // 페이지 새로고침
            } else {
                alert('IP 저장에 실패했습니다: ' + data.message);
            }
        })
        .catch(error => {
            console.error('Error saving IP:', error);
            alert('IP 저장에 실패했습니다.');
        });
    }

function deleteIp(id) {
    // Delete IP logic here
    // For example, send a DELETE request to the server
    fetch('/delete/' + id, {
        method: 'DELETE'
    })
        .then(response => response.json())
        .then(data => {
            if (data.isSuccess) {
                alert('IP가 성공적으로 삭제되었습니다.');
                location.reload(); // 페이지 새로고침
            } else {
                alert('IP 삭제에 실패했습니다: ' + data.message);
            }
        })
        .catch(error => {
            console.error('Error deleting IP:', error);
            alert('IP 삭제에 실패했습니다.');
        });
}

function toggleForm() {
    var form = document.getElementById('ipForm');
    if (form.style.display === 'none') {
        form.style.display = 'block';
    } else {
        form.style.display = 'none';
    }
}