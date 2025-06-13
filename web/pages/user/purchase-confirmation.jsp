<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pesanan Berhasil | Sekenly</title>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/navbar.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/footer.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/purchase-confirmation.css">

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
</head>
<body>
<%
    // Redirect if not coming from successful order submission
    Boolean orderSuccess = (Boolean) request.getAttribute("orderSuccess");
    if (orderSuccess == null || !orderSuccess) {
        response.sendRedirect(request.getContextPath() + "/");
        return;
    }
%>

<jsp:include page="../../components/navbar.jsp" />

<div class="confirmation-container">
    <div class="success-icon">
        <svg xmlns="http://www.w3.org/2000/svg" width="80" height="80" viewBox="0 0 80 80" fill="none">
            <circle cx="40" cy="40" r="40" fill="#047857" fill-opacity="0.1"/>
            <path d="M36 46.17L31.41 41.59L30 43L36 49L50 35L48.59 33.59L36 46.17Z" fill="#047857"/>
        </svg>
    </div>

    <h1>Pesanan Berhasil!</h1>

    <p class="message">
        Terima kasih atas pesanan Anda. Pesanan Anda telah berhasil diproses dan sedang menunggu konfirmasi.
    </p>

    <p class="status-info">
        Status pesanan saat ini: <span class="status">Menunggu Konfirmasi</span>
    </p>

    <p class="instruction">
        Tim kami akan segera memproses pesanan Anda. Anda dapat melihat status pesanan di halaman akun Anda.
    </p>

    <div class="buttons">
        <a href="<%=request.getContextPath()%>/" class="continue-shopping">
            Lanjutkan Belanja
        </a>
        <a href="<%=request.getContextPath()%>/auth/dashboard" class="view-dashboard">
            Lihat Dashboard
        </a>
    </div>
</div>

<jsp:include page="../../components/footer.jsp" />
</body>
</html>
