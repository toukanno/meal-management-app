# meal-management-app

食事管理アプリ（Android / Kotlin / Jetpack Compose）です。  
食事ログの登録・一覧表示・合計カロリー表示を行えます。

## 実装済み機能
- 食事タイプ（朝食/昼食/夕食/間食）の選択
- メニュー名・カロリー・メモの入力
- ローカルDB（Room）への保存
- 登録履歴の一覧表示
- 合計カロリーの自動集計表示
- 入力バリデーション（必須項目チェック）

## 技術構成
- Kotlin
- Jetpack Compose + Material3
- ViewModel + StateFlow
- Room（オフライン保存）

## ディレクトリ概要
- `app/src/main/java/com/example/mealmanagementapp/data`: DB/Repository層
- `app/src/main/java/com/example/mealmanagementapp/viewmodel`: ViewModelとUI状態
- `app/src/main/java/com/example/mealmanagementapp/ui`: Compose UI

## 開発環境セットアップ
1. Android Studio (Ladybug 以降推奨) をインストール
2. Android SDK 35 を導入
3. プロジェクトを開いて Sync 実行
4. `debug` ビルドで動作確認

## リリース手順（Google Play）

### 1) 署名鍵を作成
```bash
keytool -genkeypair -v \
  -keystore release-keystore.jks \
  -alias mealapp \
  -keyalg RSA -keysize 2048 -validity 10000
```

### 2) 環境変数を設定
```bash
export RELEASE_STORE_FILE=/absolute/path/release-keystore.jks
export RELEASE_STORE_PASSWORD=*****
export RELEASE_KEY_ALIAS=mealapp
export RELEASE_KEY_PASSWORD=*****
```

### 3) リリースビルド
```bash
./gradlew clean bundleRelease
```

生成物:
- `app/build/outputs/bundle/release/app-release.aab`

### 4) Play Console 申請
- アプリ作成（デフォルト言語・カテゴリ設定）
- ストア掲載情報（説明文・スクリーンショット・アイコン）
- データセーフティ入力
- テストトラック（内部テスト）配信
- 審査提出

## 今後の改善候補
- 食事の編集/削除
- 日付別の集計・グラフ化
- 写真添付
- Firebase連携によるバックアップ
