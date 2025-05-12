export function isTokenExpired(token) {
    if (!token) return true

    try {
        const payload = JSON.parse(atob(token.split('.')[1]))
        const exp = payload.exp * 1000  // 초 → 밀리초
        return Date.now() > exp
    } catch (e) {
        console.error('토큰 파싱 실패', e)
        return true
    }
}
