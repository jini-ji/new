<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>🎧 NFT 상세 정보</h2>

<div class="nft-detail-box">
  <p><strong>MUSIC NO:</strong> ${nft.musicNo}</p>
  <p><strong>민팅 해시:</strong> ${nft.mintHash}</p>
  <p><strong>발행일:</strong> ${nft.mintDate}</p>
  <p><strong>내 지갑 주소:</strong> ${nft.walletAddress}</p>

  <audio controls>
    <source src="/resources/music/sample_${nft.musicNo}.mp3" type="audio/mp3" />
    Your browser does not support the audio element.
  </audio>

  <!-- 실제 metadata 링크나 블록 탐색기 연결도 가능 -->
</div>