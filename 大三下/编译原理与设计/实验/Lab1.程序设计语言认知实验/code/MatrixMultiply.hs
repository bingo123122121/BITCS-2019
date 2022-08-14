import Data.List (transpose)
import Data.Time.Clock

matrixMultiply :: [[Int]] -> [[Int]] -> [[Int]]
-- $Ϊ����Ӧ�÷������ڽ��ͺ������ȼ��������ҽ����
-- transpose������ת��
matrixMultiply a b = [ [ sum $ zipWith (*) x y | y <- transpose b] | x <- a ]

readMatrix :: [String] -> [[Int]]
readMatrix a = f a
    where
        f = map $ map read . words


main = do
    -- ��ʱ
    start <- getCurrentTime

    -- ����ģ��
    let file_in = "data.txt"
    content <- readFile file_in
    let line = lines content
        n = read (head line) :: Int
        a = tail $ take (n + 1) line
        b = drop (n + 2) line

    -- ����ģ��
    let matrix1 = readMatrix a
        matrix2 = readMatrix b
        matrix3 = matrixMultiply matrix1 matrix2

    -- ���ģ��
    -- unwords������������б����ӳ��ַ���
    let temp = map (unwords . map show) matrix3
        out = unlines temp
        file_out = "result_haskell.txt"
    writeFile file_out out

    --��ʱ
    end <- getCurrentTime
    print $ diffUTCTime end start
