保存登录状态的两种方式：
1. 保存cookie，验证cookie状态，正常状态直接返回 查询订单进行验证https://order.jd.com/center/list.action
2. 保存tickey，获取cookie，验证状态 根据tickey查询cookie：https://passport.jd.com/uc/qrCodeTicketValidation?t={ticket}