# 🏢 근태 관리 자동화 시스템  

🚀 기존 수기 방식의 출퇴근 기록을 자동화하기 위해 **카드 리더기와 API 서버를 연동한 근태 관리 시스템**을 개발하였습니다.  

## 🛠 기술 스택  
- **JAVA**: 카드 리더기 시리얼 데이터 수신 및 근태 API 서버 HTTP 요청
- **시리얼 통신**: 카드 리더기 ↔ 시스템 간 데이터 전송  
- **HTTP API 요청**: 카드 ID를 포함한 Payload 전송  
- **Spring Framework**: 근태 API 서버 개발 및 데이터 처리  
- **MySQL**: 근태 기록 저장  

## 🎯 개발 계기  
어머니께서 운영하시는 가게에서 아르바이트생의 출퇴근 기록을 **종이에 수기로 작성**하시는 불편함을 보고,  
이를 **디지털화하여 보다 효율적으로 관리**할 수 있도록 직접 개발하게 되었습니다.  

## 📌 주요 기능  
1. **카드 ID 수신**  
   - RFID 리더기가 인식한 **카드 UID를 시리얼 포트**를 통해 읽어옴  
2. **근태 API 서버와 연동**  
   - 카드 ID를 JSON 형식의 **Payload**로 변환 후 HTTP 요청(`POST`)  
3. **직원 정보 검증**  
   - **DB에는 카드 UID와 직원의 정보(이름, 카드UID, 근로여부)를 저장**
   - 서버에서 수신한 **카드 UID를 DB의 직원 정보와 매치하여 유효 직원 여부 판단**
4. **출퇴근 기록 저장**  
   - 근로 중인 직원의 경우 **DB에 출퇴근 날짜,시각과 함께 저장**

## ✅ 기대 효과  
- ✅ **출퇴근 프로세스 자동화** → 기존 수기 입력 방식 대비 **업무 효율 향상**  
- ✅ **실시간 데이터 처리** → API 연동으로 빠른 응답 속도 제공  
- ✅ **업무 부담 감소** → 가게 운영의 효율성 증가 및 관리 편의성 개선  

## 주요 코드

### 카드 리더기 시리얼 포트 연결 코드
```java
    // 시리얼 포트 초기화 및 연결
    private static void setupSerialPort() {
        comPort = SerialPort.getCommPort("COM3"); // 가게 포스기의 여유분 COM포트 설정
        comPort.setBaudRate(BAUD_RATE);

        if (comPort.openPort()) {
            System.out.println("리더기 인식에 성공했습니다.");
            trayIcon.displayMessage("인식성공", "리더기 인식에 성공했습니다.", TrayIcon.MessageType.INFO);
        } else {
            System.out.println("리더기 인식에 실패하였습니다.");
            trayIcon.displayMessage("인식실패", "리더기 인식에 실패했습니다.", TrayIcon.MessageType.ERROR);
            System.exit(1);
        }
    }
```

### 시리얼 포트를 통해 카드ID 수신 및 근태API 서버 HTTP 요청 코드
```java
// 시리얼 포트에서 카드 UID 지속 수신
    private static void readSerialData() {
        new Thread(() -> {
            HttpURLConnection conn = null;
            OutputStream os = null;
            
            try {
                URI uri = new URI("https://jsonplaceholder.typicode.com/posts");
                URL url = uri.toURL();
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
    
                byte[] buffer = new byte[64];
    
                // 포트 연결 및 데이터 읽기
                try (InputStream in = comPort.getInputStream()) {
                    while (true) {
                        if (in.available() > 0) {
                            int numRead = in.read(buffer);
                            if (numRead > 0) {
                                String cardUID = new String(buffer, 0, numRead, StandardCharsets.UTF_8).trim();
                                String jsonInputString = "{\"cardUID\": \"" + cardUID + "\"}";
                                
                                // 데이터 전송
                                os = conn.getOutputStream();
                                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                                os.write(input, 0, input.length);
                                os.flush();
    
                                // 응답 코드 확인
                                int responseCode = conn.getResponseCode();
                                System.out.println(responseCode);
                                if (responseCode >= 200 && responseCode < 300) {
                                    trayIcon.displayMessage("출결 전송 완료", "카드 번호 :: " + cardUID, TrayIcon.MessageType.INFO);
                                } else {
                                    trayIcon.displayMessage("출결 전송 실패", "카드 번호 :: " + cardUID, TrayIcon.MessageType.ERROR);
                                }
                            }
                        }
                        Thread.sleep(100); // 딜레이 추가
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (ConnectException e) {
                trayIcon.displayMessage("연결 실패", "서버에 연결할 수 없습니다.", TrayIcon.MessageType.ERROR);
            } catch (Exception e) {
                trayIcon.displayMessage("연결 실패", "서버에 연결할 수 없습니다.", TrayIcon.MessageType.ERROR);
            } finally {
                // 리소스 정리
                if (conn != null) {
                    conn.disconnect();
                }
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (comPort != null) {
                    comPort.closePort();
                    System.out.println("COM3 포트 연결이 해제 되었습니다.");
                }
            }
        }).start();
    }
```
### 프로그램 트레이 아이콘
![image](https://github.com/user-attachments/assets/c22b01fc-6dae-44e3-8ea0-5febe73309b6)

### 프로그램 실행시
![image](https://github.com/user-attachments/assets/cabc76a0-db04-438c-a554-5732610b64c1)

### 프로그램 실행 후 리더기에 카드를 인식할 시
![image](https://github.com/user-attachments/assets/87dbdbb6-2fd7-4891-9bfb-8cfe3c929e54)

---
