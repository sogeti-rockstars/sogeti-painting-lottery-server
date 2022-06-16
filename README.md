# sogeti-lotteryItem-lottery-server
 
 
 
REQUEST PATH                                 RESPONSE DESCIPTION
 
 
 
GET:  /api/v1/info                         | Array with all paths
 
GET:  /api/v1/info/{id}                    | get(@PathVariable Long id) {
 
DEL:  /api/v1/info/{id}                    | deletePost(@PathVariable Long id) {
 
POST: /api/v1/info                         | ResponseEntity<AssociationInfo> addNew(@RequestBody AssociationInfo infoItem) {
 
PUT:  /api/v1/info/{id}                    | update(@PathVariable Long id, @RequestBody AssociationInfo infoItem) {
 
GET:  /api/v1/info/field                   | Map<String, String> getAllMap() {
 
GET:  /api/v1/info/field/{fieldName}       | get(@PathVariable String fieldName) {
 
DEL:  /api/v1/info/field/{fieldName}       | deletePost(@PathVariable String fieldName) {
 
PUT:  /api/v1/info/field/{fieldName}       | update(@PathVariable String fieldName, @RequestBody String data) {
 
POST: /api/v1/info/field/{fieldName}       | addNew(@PathVariable String fieldName, @RequestBody String data) {
 
 
 
PATH: /api/v1/users      |
 
GET:/api/v1/users/current                  | user(Principal user) {
 
PUT:/api/v1/users/logout                   | logout() {
 
PUT:/api/v1/users/password                 | setAdminPass(@RequestBody String newPass) {
 
 
 
PATH: /api/v1/contestant |
 
GET:/api/v1/contestant                     | List<?> getAll() {
 
GET:/api/v1/contestant/{id}                | get(@PathVariable Long id) {
 
DEL:/api/v1/contestant/{id}                | deletePost(@PathVariable Long id) {
 
POST:/api/v1/contestant                    | ResponseEntity<Contestant> addNew(@RequestBody Contestant cont) {
 
PUT:/api/v1/contestant/{id}                | update(@PathVariable Long id, @RequestBody Contestant cont) {
 
 
 
PATH: /api/v1/lottery
 
GET:/api/v1/lottery                        | List<Lottery> getAll() {
 
GET:/api/v1/lottery/summary                | getLotteryList() {
 
GET:/api/v1/lottery/{id}                   | get(@PathVariable Long id) {
 
GET:/api/v1/lottery/{id}/winners           | getWinners(@PathVariable Long id) {
 
GET:/api/v1/lottery/{id}/items             | getLotteryItems(@PathVariable Long id) {
 
GET:/api/v1/lottery/{id}/available-items   | getAvailableLotteryItems(@PathVariable Long id) {
 
PUT:/api/v1/lottery/{id}/spin              | spinTheWheelNoItem(@PathVariable Long id) {
 
DEL:/api/v1/lottery/{id}                   | deletePost(@PathVariable Long id) {
 
POST:/api/v1/lottery                       | ResponseEntity<Lottery> addNew(@RequestBody Lottery lottery) {
 
PUT:/api/v1/lottery/{id}                   | update(@PathVariable Long id, @RequestBody Lottery lottery) {
 
 
 
PATH: /api/v1/item       |
 
GET:/api/v1/item                           | getAll() {
 
GET:/api/v1/item/{id}                      | get(@PathVariable Long id) {
 
DEL:/api/v1/item/{id}                      | deletePost(@PathVariable Long id) {
 
PUT:/api/v1/item/{id}                      | update(@PathVariable Long id, @RequestBody LotteryItem item) {
 
POST:/api/v1/item                          | addNew(@RequestParam Long lotteryId, @RequestBody LotteryItem lotteryItem) {
 
GET:/api/v1/item/image/{id}"               | getImage(@PathVariable Long id) throws IOException { produces = MediaType.IMAGE_JPEG_VALUE)
 
PUT:/api/v1/item/update-image/{id}         | uploadPicture(@PathVariable Long id, @RequestPart/image") MultipartFile multipartFile) throws IOException {
 
 
 
PATH: /api/v1/winner
 
GET:/api/v1/winner                         | List<Winner> getAll() {
 
GET:/api/v1/winner/{id}                    | get(@PathVariable Long id) {
 
DEL:/api/v1/winner/{id}                    | deletePost(@PathVariable Long id) {
 
PUT:/api/v1/winner/{id}                    | update(@PathVariable Long id, @RequestBody Winner winner) {
 
 
 
 
