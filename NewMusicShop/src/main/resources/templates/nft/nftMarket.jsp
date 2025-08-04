<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, com.kh.spring.nft.model.vo.NFT" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    // 테스트용 NFT 데이터
    List<NFT> nftList = new ArrayList<>();
    for (int i = 1; i <= 6; i++) {
        NFT n = new NFT();
        n.setMusicNo(i);
        n.setMintHash("0xArtNFT" + i);
        n.setMintDate(new java.util.Date());
        nftList.add(n);
    }
    request.setAttribute("nftList", nftList);
%>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>💎 아트 마켓</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <style>
    body {
      background: linear-gradient(145deg, #0f0f0f, #1a1a1a);
      color: white;
      font-family: 'Segoe UI', sans-serif;
    }
    h2 {
      font-weight: bold;
      text-align: center;
      margin-bottom: 40px;
      text-shadow: 0 0 8px #0ff;
    }
    .nft-card {
      background: rgba(255, 255, 255, 0.05);
      border: 1px solid #444;
      border-radius: 15px;
      box-shadow: 0 0 12px rgba(0,255,255,0.2);
      padding: 20px;
      margin-bottom: 25px;
      transition: all 0.3s ease-in-out;
    }
    .nft-card:hover {
      transform: translateY(-5px);
      box-shadow: 0 0 20px rgba(0,255,255,0.5);
    }
    .nft-title {
      font-size: 1.3rem;
      font-weight: bold;
      color: #00ffff;
    }
    .nft-meta, .nft-price {
      font-size: 0.95rem;
      color: #ccc;
    }
    .nft-price {
      font-weight: bold;
      color: #ffcc00;
      margin-top: 15px;
    }
    .btn-buy {
      margin-top: 15px;
      background: linear-gradient(to right, #ff00cc, #3333ff);
      color: white;
      border: none;
      border-radius: 25px;
      padding: 8px 20px;
      width: 100%;
      font-weight: bold;
      box-shadow: 0 0 10px rgba(255,0,255,0.4);
    }
    .btn-buy:hover {
      background: linear-gradient(to right, #3333ff, #ff00cc);
    }
    .btn-back {
      background: transparent;
      border: 1px solid #777;
      color: #aaa;
      border-radius: 25px;
      padding: 8px 20px;
    }
    .btn-back:hover {
      background: #111;
      color: #fff;
    }
  </style>
</head>
<body>
<div class="container mt-5">
  <h2>💠 아트 마켓 (네온 에디션)</h2>

  <c:choose>
    <c:when test="${empty nftList}">
      <div class="alert alert-warning text-center">현재 판매 중인 NFT가 없습니다.</div>
    </c:when>
    <c:otherwise>
      <div class="row">
        <c:forEach var="nft" items="${nftList}">
          <div class="col-md-4">
            <div class="nft-card">
              <div class="nft-title">🎨 ART NO: ${nft.musicNo}</div>
              <div class="nft-meta mt-2">🔗 민팅 해시: ${nft.mintHash}</div>
              <div class="nft-meta mt-2">📅 발행일: 
                <fmt:formatDate value="${nft.mintDate}" pattern="yyyy-MM-dd HH:mm" />
              </div>
              <div class="nft-price">💰 가격: 25,000₩</div>
              <form action="#" method="post">
                <button type="submit" class="btn btn-buy">🎁 소장하기</button>
              </form>
            </div>
          </div>
        </c:forEach>
      </div>
    </c:otherwise>
  </c:choose>

  <div class="text-center mt-5">
    <a href="${pageContext.request.contextPath}/" class="btn btn-back">⬅ 메인으로 돌아가기</a>
  </div>
</div>
</body>
</html>