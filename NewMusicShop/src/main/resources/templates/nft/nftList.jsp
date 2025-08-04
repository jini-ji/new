<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

<style>
  .nft-card {
    background: white;
    border-radius: 15px;
    box-shadow: 0 5px 15px rgba(0,0,0,0.1);
    padding: 20px;
    margin-bottom: 25px;
    transition: 0.3s;
  }
  .nft-card:hover {
    transform: scale(1.02);
  }
  .nft-title { font-size: 1.2rem; font-weight: bold; color: #222; }
  .nft-meta { font-size: 0.95rem; color: #555; }
  .nft-price { font-size: 1rem; color: #17a2b8; font-weight: bold; }
  .btn-purchase {
    margin-top: 10px;
    background: linear-gradient(to right, #00c6ff, #0072ff);
    color: white;
    border: none;
    border-radius: 25px;
    padding: 8px 20px;
  }
</style>

<div class="container mt-5">
  <h2 class="text-center mb-4">🪙 판매 중인  음원 목록</h2>

  <c:choose>
    <c:when test="${empty myNFTs}">
      <div class="alert alert-warning text-center">현재 판매 중인 NFT가 없습니다.</div>
    </c:when>
    <c:otherwise>
      <div class="row">
        <c:forEach var="nft" items="${myNFTs}">
          <div class="col-md-4">
            <div class="nft-card">
              <div class="nft-title">🎵 MUSIC NO: ${nft.musicNo}</div>
              <div class="nft-meta">🔗 민팅 해시:<br><small>${nft.mintHash}</small></div>
              <div class="nft-meta">📅 발행일:
                <fmt:formatDate value="${nft.mintDate}" pattern="yyyy-MM-dd HH:mm" />
              </div>
              <div class="nft-price mt-2">💰 가격: 10,000₩</div>
              <button class="btn btn-purchase w-100 mt-2">🛒 구매하기</button>
            </div>
          </div>
        </c:forEach>
      </div>
    </c:otherwise>
  </c:choose>

  <div class="text-center mt-5">
    <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">⬅ 메인으로 가기</a>
  </div>
</div>