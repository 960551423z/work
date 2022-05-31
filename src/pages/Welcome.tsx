import React from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import { Card, Alert, Typography } from 'antd';
import styles from './Welcome.less';

const CodePreview: React.FC = ({ children }) => (
  <pre className={styles.pre}>
    <code>
      <Typography.Text copyable>{children}</Typography.Text>
    </code>
  </pre>
);

const Welcome: React.FC = () => {
  return (
    <PageContainer>
      <Card>
        <Alert
          message={'欢迎来到用户中心  '}
          type="success"
          showIcon
          banner
          style={{
            margin: -12,
            marginBottom: 24,
          }}
        />
        <Typography.Text strong>
            联系小张
        </Typography.Text>
        <CodePreview>QQ: 960551423</CodePreview>

        <Typography.Text
          strong
          style={{
            marginBottom: 12,
          }}
        >
        </Typography.Text>
        <CodePreview>vx: 15571321851</CodePreview>
      </Card>
    </PageContainer>
  );
};

export default Welcome;
