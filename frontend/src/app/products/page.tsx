export default function Page() {
    return (
        <>
            <h1>Grids & Circles</h1>

            <h2>상품목록</h2>

            {/* 이후 DB로 받을 예정 */}
            <div>상품1</div>
            <div>상품2</div>
            <div>상품3</div>


            <h2>장바구니</h2>
            

            <div>이메일</div>
            <input type="text" className="border-2 border-black"/>

            <div>주소</div>
            <input type="text" className="border-2 border-black"/>

            <div>우편주소</div>
            <input type="text" className="border-2 border-black"/>

            <div>총금액</div>
            
            <button>주문하기</button>
        </>
    )
}